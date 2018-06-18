/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FrTestModule } from '../../../test.module';
import { InspeccionMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/inspeccion-my-suffix/inspeccion-my-suffix-detail.component';
import { InspeccionMySuffixService } from '../../../../../../main/webapp/app/entities/inspeccion-my-suffix/inspeccion-my-suffix.service';
import { InspeccionMySuffix } from '../../../../../../main/webapp/app/entities/inspeccion-my-suffix/inspeccion-my-suffix.model';

describe('Component Tests', () => {

    describe('InspeccionMySuffix Management Detail Component', () => {
        let comp: InspeccionMySuffixDetailComponent;
        let fixture: ComponentFixture<InspeccionMySuffixDetailComponent>;
        let service: InspeccionMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [InspeccionMySuffixDetailComponent],
                providers: [
                    InspeccionMySuffixService
                ]
            })
            .overrideTemplate(InspeccionMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InspeccionMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InspeccionMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new InspeccionMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.inspeccion).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
