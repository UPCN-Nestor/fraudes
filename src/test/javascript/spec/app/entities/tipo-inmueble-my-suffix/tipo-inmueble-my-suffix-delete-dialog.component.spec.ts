/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FrTestModule } from '../../../test.module';
import { TipoInmuebleMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/tipo-inmueble-my-suffix/tipo-inmueble-my-suffix-delete-dialog.component';
import { TipoInmuebleMySuffixService } from '../../../../../../main/webapp/app/entities/tipo-inmueble-my-suffix/tipo-inmueble-my-suffix.service';

describe('Component Tests', () => {

    describe('TipoInmuebleMySuffix Management Delete Component', () => {
        let comp: TipoInmuebleMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<TipoInmuebleMySuffixDeleteDialogComponent>;
        let service: TipoInmuebleMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [TipoInmuebleMySuffixDeleteDialogComponent],
                providers: [
                    TipoInmuebleMySuffixService
                ]
            })
            .overrideTemplate(TipoInmuebleMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipoInmuebleMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoInmuebleMySuffixService);
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
