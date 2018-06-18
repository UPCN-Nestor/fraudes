/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FrTestModule } from '../../../test.module';
import { EtapaMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/etapa-my-suffix/etapa-my-suffix-delete-dialog.component';
import { EtapaMySuffixService } from '../../../../../../main/webapp/app/entities/etapa-my-suffix/etapa-my-suffix.service';

describe('Component Tests', () => {

    describe('EtapaMySuffix Management Delete Component', () => {
        let comp: EtapaMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<EtapaMySuffixDeleteDialogComponent>;
        let service: EtapaMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [EtapaMySuffixDeleteDialogComponent],
                providers: [
                    EtapaMySuffixService
                ]
            })
            .overrideTemplate(EtapaMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EtapaMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtapaMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
